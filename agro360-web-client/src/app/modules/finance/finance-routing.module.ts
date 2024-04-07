import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent as TransactionIndexPageComponent } from './transaction/index.page.component';

const routes: Routes = [
    { path: 'finance/transaction', component: TransactionIndexPageComponent },
    { path: 'finance/transaction/index', component: TransactionIndexPageComponent },
    //{ path: 'finance/transaction/create', component: TransactionEditPageComponent },
    //{ path: 'finance/transaction/edit/:transactionCode', component: TransactionEditPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class FinanceRoutingModule { }
