import { ChevronRight, Home } from "lucide-react";
import { Link } from "react-router-dom";

export interface BreadcrumbItem {
  label: string;
  to?: string;
}

export type BreadcrumbVariant = "default" | "pill";

export function PageBreadcrumb({ items, variant = "default" }: { items: BreadcrumbItem[]; variant?: BreadcrumbVariant }) {
  if (items.length === 0) return null;

  return (
    <nav className={"breadcrumb shadcn-breadcrumb" + (variant === "pill" ? " breadcrumb-pill" : "")} aria-label="Breadcrumb">
      <ol className="breadcrumb-list">
        <li className="breadcrumb-item">
          <Link className="breadcrumb-link" to="/">
            {variant === "default" && <Home size={15} aria-hidden="true" />}
            Início
          </Link>
        </li>
        {items.map((item, index) => {
          const isLast = index === items.length - 1;
          return (
            <li className="breadcrumb-item" key={item.to ?? item.label}>
              <ChevronRight className="breadcrumb-separator" size={15} aria-hidden="true" />
              {item.to && !isLast ? (
                <Link className="breadcrumb-link" to={item.to}>
                  {item.label}
                </Link>
              ) : (
                <span className={isLast ? "breadcrumb-current" : "breadcrumb-link"} aria-current={isLast ? "page" : undefined}>{item.label}</span>
              )}
            </li>
          );
        })}
      </ol>
    </nav>
  );
}
