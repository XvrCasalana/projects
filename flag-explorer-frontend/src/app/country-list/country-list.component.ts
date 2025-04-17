import { Component, OnInit } from '@angular/core';
import { Country } from "../country"
import { Router } from '@angular/router';
import { CountryService } from '../country.service';
@Component({
  selector: 'app-country-list',
  standalone: false,
  templateUrl: './country-list.component.html',
  styleUrl: './country-list.component.css'
})
export class CountryListComponent implements OnInit {
  
  countries: Country[] | undefined;

  constructor(private countryService: CountryService, private router: Router){}

  ngOnInit(): void {
      this.getCountries();
  }

  private getCountries(){
    this.countryService.getCountries().subscribe(data => {
        this.countries = data;
    });
  }

  goToDetails(name: any) {
    this.router.navigate(['/countries', name]);
  }
}
