import { Route, Routes, Navigate } from "react-router-dom";
import { AppShell } from "./components/AppShell";
import { BRAND_LOGO_URL } from "./brand";
import ProdutosPage from "./pages/ProdutosPage";
import ClientesPage from "./pages/ClientesPage";
import PedidosPage from "./pages/PedidosPage";
import UsuariosPage from "./pages/UsuariosPage";
import IncioPage from "./pages/IncioPage";

export default function App() {
  return (
    <AppShell brand={`Loja de Bolos`} logoUrl={BRAND_LOGO_URL} links={[{"label":"Início","to":"/"},{"label":"Produtos","to":"/produtos"},{"label":"Clientes","to":"/clientes"},{"label":"Pedidos","to":"/pedidos"},{"label":"Usuarios","to":"/usuarios"}]} layout="sidebar">
      <Routes>
          <Route path="/produtos" element={<ProdutosPage />} />
          <Route path="/clientes" element={<ClientesPage />} />
          <Route path="/pedidos" element={<PedidosPage />} />
          <Route path="/usuarios" element={<UsuariosPage />} />
          <Route path="/" element={<IncioPage />} />
          <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </AppShell>
  );
}
