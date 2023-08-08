type MenuType = 'container' | 'link' | 'child-link' | 'header' | 'divider';

/**
 * Mod
 */
export interface MenuModel{
    id:string;
    url?: string;
    label: string;
    icon?:string;
    children?:MenuModel[];
    type:MenuType;
}