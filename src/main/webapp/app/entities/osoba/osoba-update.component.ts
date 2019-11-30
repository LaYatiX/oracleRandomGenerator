import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IOsoba, Osoba } from 'app/shared/model/osoba.model';
import { OsobaService } from './osoba.service';
import { IAdres } from 'app/shared/model/adres.model';
import { AdresService } from 'app/entities/adres/adres.service';
import { ISklep } from 'app/shared/model/sklep.model';
import { SklepService } from 'app/entities/sklep/sklep.service';

@Component({
  selector: 'jhi-osoba-update',
  templateUrl: './osoba-update.component.html'
})
export class OsobaUpdateComponent implements OnInit {
  isSaving: boolean;

  adres: IAdres[];

  skleps: ISklep[];

  editForm = this.fb.group({
    id: [],
    imie: [],
    nazwisko: [],
    telefon: [],
    pesel: [],
    adres: [],
    sklep: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected osobaService: OsobaService,
    protected adresService: AdresService,
    protected sklepService: SklepService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ osoba }) => {
      this.updateForm(osoba);
    });
    this.adresService.query({ filter: 'osoba-is-null' }).subscribe(
      (res: HttpResponse<IAdres[]>) => {
        if (!this.editForm.get('adres').value || !this.editForm.get('adres').value.id) {
          this.adres = res.body;
        } else {
          this.adresService
            .find(this.editForm.get('adres').value.id)
            .subscribe(
              (subRes: HttpResponse<IAdres>) => (this.adres = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.sklepService
      .query()
      .subscribe((res: HttpResponse<ISklep[]>) => (this.skleps = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(osoba: IOsoba) {
    this.editForm.patchValue({
      id: osoba.id,
      imie: osoba.imie,
      nazwisko: osoba.nazwisko,
      telefon: osoba.telefon,
      pesel: osoba.pesel,
      adres: osoba.adres,
      sklep: osoba.sklep
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const osoba = this.createFromForm();
    if (osoba.id !== undefined) {
      this.subscribeToSaveResponse(this.osobaService.update(osoba));
    } else {
      this.subscribeToSaveResponse(this.osobaService.create(osoba));
    }
  }

  private createFromForm(): IOsoba {
    return {
      ...new Osoba(),
      id: this.editForm.get(['id']).value,
      imie: this.editForm.get(['imie']).value,
      nazwisko: this.editForm.get(['nazwisko']).value,
      telefon: this.editForm.get(['telefon']).value,
      pesel: this.editForm.get(['pesel']).value,
      adres: this.editForm.get(['adres']).value,
      sklep: this.editForm.get(['sklep']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOsoba>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAdresById(index: number, item: IAdres) {
    return item.id;
  }

  trackSklepById(index: number, item: ISklep) {
    return item.id;
  }
}
