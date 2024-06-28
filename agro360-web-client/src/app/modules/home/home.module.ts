import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { CompteBlockComponent } from './dashboard/compte.block.component';
import { IndexPageComponent } from './dashboard/index.page.component';
import { HomeRoutingModule } from './home-routing.module';

@NgModule({
    imports: [ 
        HomeRoutingModule,
        SharedModule,        
        IndexPageComponent,
        CompteBlockComponent
    ],
    exports:[        
        IndexPageComponent,
        CompteBlockComponent
    ],
})
export class HomeModule { }