import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index/index.page.component';

const routes: Routes = [    
    { path: 'achat', pathMatch: 'full', redirectTo: 'achat/index' },
    { path: 'achat/index', component: IndexPageComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AchatRoutingModule { }
