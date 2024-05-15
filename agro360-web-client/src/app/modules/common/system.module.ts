import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { UIConfig, UIService } from './service/ui.service';
import { AuthService } from './service/auth.service';

@NgModule({
    imports: [ 
       
    ],
    exports: [
                
    ],
    providers:[
        UIService,   
        AuthService     
    ]
})
export class SystemModule {

    constructor(@Optional() @SkipSelf() parentModule?: SystemModule) {
        if (parentModule) {
          throw new Error(
            'SystemModule is already loaded. Import it in the AppModule only');
        }
    }

    static forRoot(config: UIConfig): ModuleWithProviders<SystemModule> {
        return {
            ngModule: SystemModule,
            providers: [{
                provide: UIConfig, useValue: config 
            }]
        };
    }
}