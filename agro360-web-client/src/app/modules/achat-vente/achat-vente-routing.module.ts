import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './commande/index.page.component';

const routes: Routes = [
    { path: 'achat-vente/commande', component: IndexPageComponent },
    { path: 'achat/bon-commande/index', component: IndexPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AchatVenteRoutingModule { }
