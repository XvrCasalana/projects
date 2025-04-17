import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CountryService } from './country.service';

describe('CountryService', () => {
  let service: CountryService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CountryService]});

    service = TestBed.inject(CountryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should fetch all countries', () => {
    const mockCountries = [{ name: 'Germany', flag: 'https://flagcdn.com/de.svg' }];
    
    service.getCountries().subscribe(countries => {
      expect(countries.length).toBe(1);
      expect(countries[0].name).toBe('Germany');
    });

    const req = httpMock.expectOne('/countries');
    expect(req.request.method).toBe('GET');
    req.flush(mockCountries);
  });

  it('should fetch countrydetails by name', () => {
    const mockDetails = {
      name: 'Germany',
      population: 83000000,
      capital: 'Berlin',
      flag: 'https://flagcdn.com/de.svg'
    };

    service.getCountryByName('Germany').subscribe(countrydetails => {
      expect(countrydetails.name).toBe('Germany');
      expect(countrydetails.capital).toBe('Berlin');
    });

    const req = httpMock.expectOne('/countries/Germany');
    expect(req.request.method).toBe('GET');
    req.flush(mockDetails);
  });

  afterEach(() => {
    httpMock.verify();
  });


});
