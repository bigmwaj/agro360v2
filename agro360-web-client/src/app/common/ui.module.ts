import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { UIConfig, UIService } from './service/ui.service';

@NgModule({
    imports: [ 
       
    ],
    exports: [
                
    ],
    providers:[
        UIService,        
    ]
})
export class UIModule {

    constructor(@Optional() @SkipSelf() parentModule?: UIModule) {
        if (parentModule) {
          throw new Error(
            'UIModule is already loaded. Import it in the AppModule only');
        }
    }

    static forRoot(config: UIConfig): ModuleWithProviders<UIModule> {
        return {
            ngModule: UIModule,
            providers: [{
                provide: UIConfig, useValue: config 
            }]
        };
    }
}