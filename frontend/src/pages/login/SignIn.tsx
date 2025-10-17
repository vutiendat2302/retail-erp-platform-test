import AuthLayout from "./AuthPageLayout";
import {SignInForm} from "../../components/auth/SignInForm";

interface LoginFormProps {
  onLogin: (useData: {username: string, role: string}) => void;
}

const mockUsers = {
    'admin': { password: 'admin123', role: 'Quản trị viên', fullName: 'Vu Tien Dat' },
  };

export function LoginForm({onLogin}: LoginFormProps) {
  return (
    <>
      <AuthLayout>
        <SignInForm onLogin={onLogin} />
      </AuthLayout>
    </>
  );
}


