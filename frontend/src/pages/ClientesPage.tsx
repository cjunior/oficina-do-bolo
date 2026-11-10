import { DataTable, ApiForm, PageBreadcrumb } from "../components";
import { Plus, X } from "lucide-react";
import { useState } from "react";
import { createPortal } from "react-dom";
import { clientesService } from "../services/clientes.service";

export default function ClientesPage() {
  const [createOpen, setCreateOpen] = useState(false);
  const [createTheme, setCreateTheme] = useState<"light" | "dark">(() => {
    if (typeof document === "undefined") return "light";
    const isDark =
      document.documentElement.getAttribute("data-theme") === "dark" ||
      document.documentElement.dataset?.theme === "dark" ||
      document.body?.getAttribute("data-theme") === "dark" ||
      document.body?.dataset?.theme === "dark" ||
      document.documentElement.getAttribute("data-swal2-theme") === "dark" ||
      (typeof window !== "undefined" && window.localStorage.getItem("theme") === "dark");
    return isDark ? "dark" : "light";
  });
  const [reloadToken, setReloadToken] = useState(0);
  const openCreateDialog = () => {
    const active = (typeof document !== "undefined" && (
      document.documentElement.getAttribute("data-theme") === "dark" ||
      document.documentElement.dataset?.theme === "dark" ||
      document.body?.getAttribute("data-theme") === "dark" ||
      document.body?.dataset?.theme === "dark" ||
      document.documentElement.getAttribute("data-swal2-theme") === "dark" ||
      (typeof window !== "undefined" && (
        window.localStorage.getItem("theme") === "dark" ||
        (!window.localStorage.getItem("theme") && Boolean(window.matchMedia?.("(prefers-color-scheme: dark)").matches))
      ))
    )) ? "dark" : "light";
    setCreateTheme(active);
    setCreateOpen(true);
  };
  const closeCreateDialog = () => setCreateOpen(false);


  return (
    <div className="page">

      <PageBreadcrumb variant="default" items={[{ label: `Listagem de Clientes` }]} />

      <header className="page-header">
        <div>
          <h1 className="page-title">{`Listagem de Clientes`}</h1>
        </div>
      </header>
      <p style={{ margin: "-18px 0 20px 0" }}>{`Gerencie clientes cadastrados, revise status e execute ações recorrentes.`}</p>
      <div className="page-actions">
        <button className="btn btn-primary" type="button" onClick={openCreateDialog}>
          <Plus size={16} aria-hidden="true" />
          Adicionar
        </button>
      </div>
      <DataTable load={clientesService.list} reloadToken={reloadToken} columns={[{"key":"nome","label":"Nome"},{"key":"email","label":"Email"},{"key":"telefone","label":"Telefone"}]} idKey={"id"} fields={[{"name":"nome","label":"Nome","type":"text","required":true,"readOnly":false},{"name":"email","label":"Email","type":"email","required":true,"readOnly":false},{"name":"telefone","label":"Telefone","type":"text","required":false,"readOnly":false}]} onUpdate={clientesService.update} onDelete={clientesService.remove} />
      {createOpen && createPortal(
        <div className="dialog-overlay" data-theme={createTheme} role="presentation" onClick={closeCreateDialog}>
          <section
            className="dialog-content shadcn-dialog"
            data-theme={createTheme}
            role="dialog"
            aria-modal="true"
            aria-labelledby="create-dialog-title"
            onClick={(event) => event.stopPropagation()}
          >
            <header className="dialog-header">
              <div>
                <h2 id="create-dialog-title" className="dialog-title">Adicionar</h2>
                <p className="dialog-description">Preencha os dados para criar um novo registro.</p>
              </div>
              <button className="icon-btn" type="button" onClick={closeCreateDialog} aria-label="Fechar">
                <X size={16} aria-hidden="true" />
              </button>
            </header>
            <ApiForm
              submit={clientesService.create}
              fields={[{"name":"nome","label":"Nome","type":"text","required":true,"readOnly":false},{"name":"email","label":"Email","type":"email","required":true,"readOnly":false},{"name":"telefone","label":"Telefone","type":"text","required":false,"readOnly":false}]}
              submitLabel="Adicionar"
              
              onSuccess={() => {
                setCreateOpen(false);
                setReloadToken((value) => value + 1);
              }}
            />
          </section>
        </div>,
        document.body
      )}
    </div>
  );
}
