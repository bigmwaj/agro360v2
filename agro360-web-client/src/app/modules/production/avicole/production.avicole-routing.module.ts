import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './cycle/index.page.component';
import { EditPageComponent } from './cycle/edit.page.component';

const routes: Routes = [
    { path: 'production/avicole/cycle', component: IndexPageComponent },
    { path: 'production/avicole/cycle/index', component: IndexPageComponent },
    { path: 'production/avicole/cycle/create', component: EditPageComponent },
    { path: 'production/avicole/cycle/edit/:cycleCode', component: EditPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProductionAvicoleRoutingModule { }
