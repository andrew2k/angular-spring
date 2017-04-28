import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Person } from './person';

@Injectable()
export class SubscribeService {

  private subscribeUrl = 'api/register';  // URL to web api

  constructor(private http: Http) { }



  // Subscribe
  public post(person: Person): Promise<String> {
    let headers = new Headers({
      'Content-Type': 'application/json'});

    return this.http
               .post(this.subscribeUrl, JSON.stringify(person), {headers: headers})
               .toPromise()
               .then(res => {console.log(res); return res.text(); })
               .catch(this.handleError);
               
               /*.then(res => {console.log(res.json()); return res.json() as Person; })*/
               
               
  }


  private handleError(error: any) {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
