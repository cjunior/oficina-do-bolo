import { Hero, ResourceOverview, PageBreadcrumb } from "../components";
import { produtosService } from "../services/produtos.service";
import { clientesService } from "../services/clientes.service";
import { pedidosService } from "../services/pedidos.service";
import { usuariosService } from "../services/usuarios.service";

export default function IncioPage() {

  return (
    <div className="page">

      <PageBreadcrumb variant="default" items={[{ label: `Bem-vindo à Loja de Bolos` }]} />

      <Hero title={`Bem-vindo ao sistema de gestão da Loja de Bolos!`} subtitle={`Gerencie produtos, pedidos e clientes com facilidade e segurança via Keycloak.`} />
      <ResourceOverview resources={[{ label: "Produtos", to: "/produtos", load: produtosService.list }, { label: "Clientes", to: "/clientes", load: clientesService.list }, { label: "Pedidos", to: "/pedidos", load: pedidosService.list }, { label: "Usuarios", to: "/usuarios", load: usuariosService.list }]} />
    </div>
  );
}
