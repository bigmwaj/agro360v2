import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
/*import { AuthGuard } from 'src/app/shared/guards/auth.guard';
import { OneColumnLeftLayoutComponent } from 'src/app/layout/components/one-column-left-layout.component';
import { CompanyComponent } from './components/company/company.component';
import { EditCompanyComponent } from './components/company/edit-company.component';
import { ConfigComponent } from './components/config/config.component';
import { EditPersonComponent } from './components/person/edit-person.component';
import { PersonComponent } from './components/person/person.component';
import { TaxComponent } from './components/tax/tax.component';
import { EditUserComponent } from './components/user/edit-user.component';
import { UserComponent } from './components/user/user.component';*/

const routes: Routes = [
    {
        path: '', 
        /*component: OneColumnLeftLayoutComponent, 
        canActivate: [AuthGuard], 
        children: [            
            { path:'user', component: UserComponent },
            { path:'user/edit', component: EditUserComponent },
            { path:'user/edit/:user', component: EditUserComponent },

            { path:'company', component: CompanyComponent },
            { path:'company/edit', component: EditCompanyComponent },
            { path:'company/edit/:company', component: EditCompanyComponent },

            { path:'person', component: PersonComponent },
            { path:'person/edit', component: EditPersonComponent },
            { path:'person/edit/:person', component: EditPersonComponent },

            { path:'tax', component: TaxComponent },

            { path:'config', component: ConfigComponent },
        ]*/
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdmingRoutingModule { }
