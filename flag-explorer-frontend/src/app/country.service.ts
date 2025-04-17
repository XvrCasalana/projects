import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Country } from './country'; 
import { Countrydetails } from './countrydetails';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private baseURL = "http://localhost:8080/countries";
  name : string | undefined;
  constructor(private httpClient: HttpClient) { }

  getCountries(): Observable<Country[]>{
     return this.httpClient.get<Country[]>(`${this.baseURL}`);
  }

  getCountryByName(name:any): Observable<Countrydetails>{
    return this.httpClient.get<Countrydetails>(`${this.baseURL + "/" + name}`);
 }
}
