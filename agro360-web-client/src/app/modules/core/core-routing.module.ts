import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './partner/index.page.component';

const routes: Routes = [
    { path: 'core/partner', component: IndexPageComponent, },
    { path: 'core/partner/index', component: IndexPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PartnerRoutingModule { }
