import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProdukt, Produkt } from 'app/shared/model/produkt.model';
import { ProduktService } from './produkt.service';
import { ITransakcja } from 'app/shared/model/transakcja.model';
import { TransakcjaService } from 'app/entities/transakcja/transakcja.service';
import { ISklep } from 'app/shared/model/sklep.model';
import { SklepService } from 'app/entities/sklep/sklep.service';

@Component({
  selector: 'jhi-produkt-update',
  templateUrl: './produkt-update.component.html'
})
export class ProduktUpdateComponent implements OnInit {
  isSaving: boolean;

  transakcjas: ITransakcja[];

  skleps: ISklep[];

  editForm = this.fb.group({
    id: [],
    nazwa: [],
    wartosc: [],
    vat: [],
    transakcja: [],
    sklep: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected produktService: ProduktService,
    protected transakcjaService: TransakcjaService,
    protected sklepService: SklepService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produkt }) => {
      this.updateForm(produkt);
    });
    this.transakcjaService
      .query()
      .subscribe(
        (res: HttpResponse<ITransakcja[]>) => (this.transakcjas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.sklepService
      .query()
      .subscribe((res: HttpResponse<ISklep[]>) => (this.skleps = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(produkt: IProdukt) {
    this.editForm.patchValue({
      id: produkt.id,
      nazwa: produkt.nazwa,
      wartosc: produkt.wartosc,
      vat: produkt.vat,
      transakcja: produkt.transakcja,
      sklep: produkt.sklep
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produkt = this.createFromForm();
    if (produkt.id !== undefined) {
      this.subscribeToSaveResponse(this.produktService.update(produkt));
    } else {
      this.subscribeToSaveResponse(this.produktService.create(produkt));
    }
  }

  private createFromForm(): IProdukt {
    return {
      ...new Produkt(),
      id: this.editForm.get(['id']).value,
      nazwa: this.editForm.get(['nazwa']).value,
      wartosc: this.editForm.get(['wartosc']).value,
      vat: this.editForm.get(['vat']).value,
      transakcja: this.editForm.get(['transakcja']).value,
      sklep: this.editForm.get(['sklep']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdukt>>) {
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

  trackTransakcjaById(index: number, item: ITransakcja) {
    return item.id;
  }

  trackSklepById(index: number, item: ISklep) {
    return item.id;
  }
}
