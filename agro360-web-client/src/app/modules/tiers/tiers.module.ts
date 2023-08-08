import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/shared/shared.module';
import { EditAddressListFormComponent } from './components/common/edit-address-list-form.component';
import { EditPersonFormComponent } from './components/common/edit-person-form.component';
import { CompanyComponent } from './components/company/company.component';
import { EditCompanyComponent } from './components/company/edit-company.component';
import { ConfigComponent } from './components/config/config.component';
import { EditPersonComponent } from './components/person/edit-person.component';
import { PersonComponent } from './components/person/person.component';
import { AddUpdateTaxModalComponent } from './components/tax/modal/add-update-tax-modal.component';
import { TaxComponent } from './components/tax/tax.component';
import { EditUserComponent } from './components/user/edit-user.component';
import { ChangePasswordModalComponent } from './components/user/modal/change-password-modal.component';
import { UserComponent } from './components/user/user.component';
import { AdmingRoutingModule } from './admin-routing.module';

@NgModule({
    declarations: [
        EditPersonFormComponent,
        EditAddressListFormComponent,
        
        CompanyComponent,
        EditCompanyComponent,

        UserComponent,
        EditUserComponent,

        TaxComponent,
        AddUpdateTaxModalComponent,

        PersonComponent,    
        EditPersonComponent,

        ConfigComponent,

        ChangePasswordModalComponent,
    ],
    imports: [
        SharedModule,
        CommonModule,
        AdmingRoutingModule,
        NgbModule,
        FormsModule, 
        ReactiveFormsModule,
    ],
    providers:[
        NgbActiveModal
    ]
})
export class AdminModule { }