import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent as StockIndexPageComponent } from './index/index.page.component';

const routes: Routes = [
    { path: 'stock', component: StockIndexPageComponent },

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class StockRoutingModule { }
