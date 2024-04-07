import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent} from './index/index.page.component';

const routes: Routes = [    
    { path: 'paie', pathMatch: 'full', redirectTo: 'paie/index' },
    { path: 'paie/index', component: IndexPageComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PaieRoutingModule { }
