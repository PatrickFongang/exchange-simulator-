"use client";

import { createContext, useContext, useCallback, useEffect, useRef } from "react";
import { useRouter } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";
import { useGetMe, getGetMeQueryKey } from "@/api/generated";
import { AXIOS_INSTANCE } from "@/api/axios-instance";
import type { UserResponseDto } from "@/api/generated";

interface AuthContextValue {
  user: UserResponseDto | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  handleLogout: () => void;
  refetchUser: () => void;
}

const AuthContext = createContext<AuthContextValue>({
  user: null,
  isAuthenticated: false,
  isLoading: true,
  handleLogout: () => {},
  refetchUser: () => {},
});

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const queryClient = useQueryClient();
  const loggingOut = useRef(false);

  const {
    data: user,
    isLoading,
    isError,
    refetch,
  } = useGetMe({
    query: {
      retry: false,
      staleTime: 5 * 60 * 1000,
    },
  });

  const handleLogout = useCallback(async () => {
    if (loggingOut.current) return;
    loggingOut.current = true;

    try {
      await AXIOS_INSTANCE.post("/api/auth/logout");
    } catch {
      // Ignore — session may already be expired
    }

    queryClient.setQueryData(getGetMeQueryKey(), null);
    queryClient.removeQueries({ queryKey: getGetMeQueryKey() });
    router.replace("/login");
    loggingOut.current = false;
  }, [queryClient, router]);

  // 401 interceptor — redirect to login on session expiry
  useEffect(() => {
    const id = AXIOS_INSTANCE.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error?.response?.status === 401 && !loggingOut.current) {
          queryClient.setQueryData(getGetMeQueryKey(), null);
          queryClient.removeQueries({ queryKey: getGetMeQueryKey() });
          router.replace("/login");
        }
        return Promise.reject(error);
      },
    );

    return () => {
      AXIOS_INSTANCE.interceptors.response.eject(id);
    };
  }, [queryClient, router]);

  const resolvedUser = isError ? null : (user as UserResponseDto | null) ?? null;

  return (
    <AuthContext.Provider
      value={{
        user: resolvedUser,
        isAuthenticated: !!resolvedUser,
        isLoading,
        handleLogout,
        refetchUser: refetch,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
