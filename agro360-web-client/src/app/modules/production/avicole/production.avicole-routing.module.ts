import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent as CycleIndexPageComponent } from './cycle/index.page.component';
import { IndexPageComponent as JourneeIndexPageComponent } from './journee/index.page.component';
import { EditPageComponent } from './cycle/edit.page.component';

const routes: Routes = [
    { path: 'production/avicole/cycle', component: CycleIndexPageComponent },
    { path: 'production/avicole/cycle/index', component: CycleIndexPageComponent },
    { path: 'production/avicole/cycle/create', component: EditPageComponent },
    { path: 'production/avicole/cycle/edit/:cycleCode', component: EditPageComponent },
    
    { path: 'production/avicole/journee/:cycleCode', component: JourneeIndexPageComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProductionAvicoleRoutingModule { }
