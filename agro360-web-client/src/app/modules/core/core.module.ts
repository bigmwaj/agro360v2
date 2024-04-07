import { NgModule } from '@angular/core';
import { PartnerRoutingModule } from './core-routing.module';
import { CoreService } from './core.service';
import { SharedModule } from 'src/app/common/shared.module';

@NgModule({
    declarations: [
        
    ],
    imports: [
        PartnerRoutingModule,
        SharedModule
    ],
    providers: [
        CoreService
    ]
})
export class PartnerModule { }