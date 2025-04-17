import { Component, OnInit } from '@angular/core';
import { Countrydetails } from "../countrydetails"
import { ActivatedRoute } from '@angular/router';
import { CountryService } from '../country.service';


@Component({
  selector: 'app-country-details',
  standalone: false,
  templateUrl: './country-details.component.html',
  styleUrl: './country-details.component.css'
})
export class CountryDetailsComponent implements OnInit {

  countrydetails: Countrydetails | undefined;
  
  constructor(private route: ActivatedRoute, private countryService: CountryService){}

  ngOnInit(): void {
     const name = this.route.snapshot.paramMap.get('name');
     console.log(name);
     if(name){
       this.countryService.getCountryByName(name).subscribe(data => this.countrydetails=data)
     }
  }
}
