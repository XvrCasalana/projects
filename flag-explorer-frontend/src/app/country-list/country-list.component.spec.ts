import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CountryListComponent } from './country-list.component';
import { CountryService } from '../country.service';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('CountryListComponent', () => {
  let component: CountryListComponent;
  let fixture: ComponentFixture<CountryListComponent>;
  let mockService: any;

  beforeEach(async () => {
    mockService = {
      getAllCountries: jasmine.createSpy().and.returnValue(of([
        { name: 'France', flag: 'https://flagcdn.com/fr.svg' }
      ]))
    };

    await TestBed.configureTestingModule({
      declarations: [CountryListComponent],
      providers: [
        { provide: CountryService, useValue: mockService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CountryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should display countries in a grid', () => {
    const flagImages = fixture.debugElement.queryAll(By.css('img'));
    expect(flagImages.length).toBe(1);
    expect(flagImages[0].nativeElement.src).toContain('fr.svg');
  });

  it('should call the service to get countries', () => {
    expect(mockService.getCountries).toHaveBeenCalled();
  });

});
