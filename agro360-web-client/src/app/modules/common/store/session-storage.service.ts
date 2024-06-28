import { Injectable } from '@angular/core';
import { UserAccountBean } from 'src/app/backed/bean.core';

@Injectable({
    providedIn: 'root'
})
export class SessionStorageService {

    private static USER_INFOS_KEY = 'user_info';

    private static TOKEN_KEY = 'token_key';

    private static TOKEN_EXPIRATION_DATE_KEY = 'token_expiration_date_key';

    private static CSRF_TOKEN_KEY = 'csrf_token_key';

    clear():void{
        window.localStorage.clear()
    }
    
    private setItem<T>(key:string, value:T | null, mapper:(t:T) => string){
        if( value != null ){
            window.localStorage.setItem(key, mapper(value));
        }else{
            window.localStorage.removeItem(key)
        }
    }

    isSessionAlive():boolean{
        const timeout = window.localStorage.getItem(SessionStorageService.TOKEN_EXPIRATION_DATE_KEY)
        const now = new Date().getTime();
        return timeout != null && now <= parseInt(timeout);
    }
    
    private getItem<T>(key:string, mapper:(t:string) => T):T | null{
        const val = window.localStorage.getItem(key);
        if(val != null && this.isSessionAlive()){
            return mapper(val)
        }
        return null;
    }
    
    set userInfos(value:UserAccountBean){
        this.setItem<UserAccountBean>(SessionStorageService.USER_INFOS_KEY, value, v => JSON.stringify(v))
    }

    get userInfos():UserAccountBean | null{
        return this.getItem<UserAccountBean>(SessionStorageService.USER_INFOS_KEY, v => JSON.parse(v))
    }

    get token():string | null{
        return this.getItem<string>(SessionStorageService.TOKEN_KEY, v => v)
    }

    set token(value:string){
        this.setItem<string>(SessionStorageService.TOKEN_KEY, value, v => v)
    }  
    
    get csrf():string | null{
        return this.getItem<string>(SessionStorageService.CSRF_TOKEN_KEY, v => v)
    }

    set csrf(value:string){
        this.setItem<string>(SessionStorageService.CSRF_TOKEN_KEY, value, v => v)
    }  

    set tokenExpirationDate(value:number){
        const now = new Date().getTime()
        this.setItem<number>(SessionStorageService.TOKEN_EXPIRATION_DATE_KEY, value, v => (v + now).toString())
    }
}
