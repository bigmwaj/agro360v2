import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent} from './index/index.page.component';

const routes: Routes = [    
    { path: 'vente', pathMatch: 'full', redirectTo: 'vente/index' },
    { path: 'vente/index', component: IndexPageComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class VenteRoutingModule { }
