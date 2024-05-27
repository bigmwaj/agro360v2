import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index/index.page.component';
import { AuthGuard } from '../common/guard/auth.guard';

const routes: Routes = [
    {        
        path: 'stock',
        canActivate: [AuthGuard], 
        children:[
            { path: '', pathMatch: 'full', redirectTo: 'index' },
            { path: 'index', component: IndexPageComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class StockRoutingModule { }
