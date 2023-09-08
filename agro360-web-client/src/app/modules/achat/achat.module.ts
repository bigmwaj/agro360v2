import { NgModule } from '@angular/core';
import { AchatRoutingModule } from './achat-routing.module';
import { SharedModule } from 'src/app/common/shared.module';
import { AchatService } from './achat.service';

@NgModule({
    declarations: [
        
    ],
    imports: [
        AchatRoutingModule,
        SharedModule
    ],
    providers: [
        AchatService
    ]
})
export class AchatModule { }