import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite' // here is the import
import svgr from 'vite-plugin-svgr'
import path from "path"

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
    svgr({
        svgrOptions: {
          icon: true,
          exportType: "named",
          namedExport: "ReactComponent",
        },
      }),], 
    resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
      p: path.resolve(__dirname, "./src/pages"),
      c: path.resolve(__dirname, "./src/components"),
    },
  },
})