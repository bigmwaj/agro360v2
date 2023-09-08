import { NgModule } from '@angular/core';
import { TiersRoutingModule } from './tiers-routing.module';
import { TiersService } from './tiers.service';
import { SharedModule } from 'src/app/common/shared.module';

@NgModule({
    declarations: [
        
    ],
    imports: [
        TiersRoutingModule,
        SharedModule
    ],
    providers: [
        TiersService
    ]
})
export class TiersModule { }