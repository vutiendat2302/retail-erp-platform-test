import React, {createContext} from 'react';
import { Outlet } from 'react-router-dom';
import { Footer } from './Footer';
import AppHeader from "./AppHeader";
import Backdrop from "./Backdrop";
import AppSidebar from "./AppSidebar";
import { SidebarProvider, useSidebar } from "@/context/SidebarContext";

interface UserData {
  username: string;
  role: string;
}

interface AuthContextType {
  userData: UserData | null;
  onLogout: () => void;
}

interface MainLayoutProps {
  userData: UserData | null;
  onLogout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);

const LayoutContent: React.FC = () => {
  const { isExpanded, isMobileOpen } = useSidebar();

  return (
    <div className="min-h-screen xl:flex">
      <div>
        <AppSidebar />
        <Backdrop />
      </div>
      <div
        className={`flex-1 transition-all duration-300 ease-in-out ${
          isExpanded ? "lg:ml-[290px]" : "lg:ml-[90px]"
        } ${isMobileOpen ? "ml-0" : ""}`}
      >
        <AppHeader />
        <div className="p-4 mx-auto max-w-(--breakpoint-2xl) md:p-6">
          <Outlet />
        </div>
      </div>
    </div>
  );
};



const MainLayout: React.FC<MainLayoutProps> = ({ userData, onLogout }) => {
  return (
    <AuthContext.Provider value={{ userData, onLogout }}>
      <SidebarProvider>
        <LayoutContent />
        <Footer />
      </SidebarProvider>
    </AuthContext.Provider>
  );
};

export default MainLayout;
