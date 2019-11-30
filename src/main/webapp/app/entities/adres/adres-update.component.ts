import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAdres, Adres } from 'app/shared/model/adres.model';
import { AdresService } from './adres.service';
import { ILokalizacja } from 'app/shared/model/lokalizacja.model';
import { LokalizacjaService } from 'app/entities/lokalizacja/lokalizacja.service';

@Component({
  selector: 'jhi-adres-update',
  templateUrl: './adres-update.component.html'
})
export class AdresUpdateComponent implements OnInit {
  isSaving: boolean;

  lokalizacjas: ILokalizacja[];

  editForm = this.fb.group({
    id: [],
    miasto: [],
    ulica: [],
    nrDomu: [],
    nrLokalu: [],
    wojewodzwtwo: [],
    powiat: [],
    gmina: [],
    kodPocztowy: [],
    kraj: [],
    lokalizacja: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected adresService: AdresService,
    protected lokalizacjaService: LokalizacjaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adres }) => {
      this.updateForm(adres);
    });
    this.lokalizacjaService.query({ filter: 'adres-is-null' }).subscribe(
      (res: HttpResponse<ILokalizacja[]>) => {
        if (!this.editForm.get('lokalizacja').value || !this.editForm.get('lokalizacja').value.id) {
          this.lokalizacjas = res.body;
        } else {
          this.lokalizacjaService
            .find(this.editForm.get('lokalizacja').value.id)
            .subscribe(
              (subRes: HttpResponse<ILokalizacja>) => (this.lokalizacjas = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(adres: IAdres) {
    this.editForm.patchValue({
      id: adres.id,
      miasto: adres.miasto,
      ulica: adres.ulica,
      nrDomu: adres.nrDomu,
      nrLokalu: adres.nrLokalu,
      wojewodzwtwo: adres.wojewodzwtwo,
      powiat: adres.powiat,
      gmina: adres.gmina,
      kodPocztowy: adres.kodPocztowy,
      kraj: adres.kraj,
      lokalizacja: adres.lokalizacja
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adres = this.createFromForm();
    if (adres.id !== undefined) {
      this.subscribeToSaveResponse(this.adresService.update(adres));
    } else {
      this.subscribeToSaveResponse(this.adresService.create(adres));
    }
  }

  private createFromForm(): IAdres {
    return {
      ...new Adres(),
      id: this.editForm.get(['id']).value,
      miasto: this.editForm.get(['miasto']).value,
      ulica: this.editForm.get(['ulica']).value,
      nrDomu: this.editForm.get(['nrDomu']).value,
      nrLokalu: this.editForm.get(['nrLokalu']).value,
      wojewodzwtwo: this.editForm.get(['wojewodzwtwo']).value,
      powiat: this.editForm.get(['powiat']).value,
      gmina: this.editForm.get(['gmina']).value,
      kodPocztowy: this.editForm.get(['kodPocztowy']).value,
      kraj: this.editForm.get(['kraj']).value,
      lokalizacja: this.editForm.get(['lokalizacja']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdres>>) {
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

  trackLokalizacjaById(index: number, item: ILokalizacja) {
    return item.id;
  }
}
