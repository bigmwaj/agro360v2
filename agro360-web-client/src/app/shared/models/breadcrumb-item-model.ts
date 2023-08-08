/**
 * Elément de la file d'ariane.
 * File d'ariane qui s'affiche dans la plupart du temps comme titre des pages
 */
export interface BreadcrumbItemModel{
    /**
     * Libelle affiché dans la file d'ariane
     */
    label: string;
    /**
     * Fonction à invoquer en cas de click
     */
    click?: () => any;

    /**
     * Lien vers lequel redirigé la page si l'on clique sur le lien
     */
    link?: any;

    /**
     * Indique que l'item est celui de la page en cours
     */
    active?:boolean;
}