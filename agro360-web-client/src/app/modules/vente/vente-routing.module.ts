import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './commande/index.page.component';

const routes: Routes = [
    { path: 'vente/commande', component: IndexPageComponent },
    { path: 'vente/commande/index', component: IndexPageComponent },
    { path: 'vente/commande/edit/:commandeCode', component: IndexPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class VenteRoutingModule { }
