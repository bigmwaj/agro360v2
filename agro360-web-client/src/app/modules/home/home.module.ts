import { NgModule } from '@angular/core';
import { HomeService } from './home.service';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexPageComponent } from './dashboard/index.page.component';
import { CompteBlockComponent } from './dashboard/compte.block.component';
import { HomeRoutingModule } from './home-routing.module';

@NgModule({
    imports: [ 
        HomeRoutingModule,
        SharedModule,        
        IndexPageComponent,
        CompteBlockComponent
    ],
    providers: [
        HomeService
    ],
    exports:[        
        IndexPageComponent,
        CompteBlockComponent
    ],
})
export class HomeModule { }