import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditPageComponent } from './tiers/edit.page.component';
import { IndexPageComponent } from './tiers/index.page.component';

const routes: Routes = [
    { path: 'tiers/tiers', component: IndexPageComponent, },
    { path: 'tiers/tiers/index', component: IndexPageComponent },
    { path: 'tiers/tiers/create', component: EditPageComponent, },
    { path: 'tiers/tiers/edit/:tiersCode', component: EditPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TiersRoutingModule { }
