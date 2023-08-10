import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditPageComponent } from './article/edit.page.component';
import { IndexPageComponent } from './article/index.page.component';

const routes: Routes = [
    { path: 'stock/article', component: IndexPageComponent },
    { path: 'stock/article/index', component: IndexPageComponent },
    { path: 'stock/article/edit/:articleCode', component: EditPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class StockRoutingModule { }
