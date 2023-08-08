import { MenuModel } from "src/app/shared/models/menu-model";

export class AdminConstant { 
    static BASE_URL = 'module/admin';

    static menu:MenuModel = {
        id: 'admin-admin',
        label:'Données & Config',
        type:'container',
        icon: 'fas fa-fw fa-cog',
        children:[
            {
                id: 'admin',
                label:'Réferentiel',
                type: 'header',
            },
            {
                id: 'admin-person',
                label:'Personnes',
                url:`/${AdminConstant.BASE_URL}/person`,
                type: 'child-link',
            },
            {
                id: 'admin-company',
                label:'Sociétés',
                url:`/${AdminConstant.BASE_URL}/company`,
                type: 'child-link',
            },
            {
                id: 'admin-user',
                label:'Utilisateurs',
                url:`/${AdminConstant.BASE_URL}/user`,
                type: 'child-link',
            },
            {
                id: 'admin-tax',
                label:'Taxes',
                url:`/${AdminConstant.BASE_URL}/tax`,
                type: 'child-link',
            },
            {
                id: 'config',
                label:'Configuration',
                type: 'header',
            },
            {
                id: 'config-variable',
                label:'Variables',
                url:`/${AdminConstant.BASE_URL}/config`,
                type: 'child-link',
            },
        ]
    }

}
