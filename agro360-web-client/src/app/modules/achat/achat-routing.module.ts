import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './bon-commande/index.page.component';

const routes: Routes = [
    { path: 'achat/bon-commande', component: IndexPageComponent },
    { path: 'achat/bon-commande/index', component: IndexPageComponent },
    { path: 'achat/bon-commande/edit/:bonCommandeCode', component: IndexPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AchatRoutingModule { }
