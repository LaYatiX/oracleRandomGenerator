import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { INieruchomosc, Nieruchomosc } from 'app/shared/model/nieruchomosc.model';
import { NieruchomoscService } from './nieruchomosc.service';
import { IAdres } from 'app/shared/model/adres.model';
import { AdresService } from 'app/entities/adres/adres.service';

@Component({
  selector: 'jhi-nieruchomosc-update',
  templateUrl: './nieruchomosc-update.component.html'
})
export class NieruchomoscUpdateComponent implements OnInit {
  isSaving: boolean;

  adres: IAdres[];

  editForm = this.fb.group({
    id: [],
    typ: [],
    iloscMieszkan: [],
    iloscMieszkancow: [],
    adres: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected nieruchomoscService: NieruchomoscService,
    protected adresService: AdresService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nieruchomosc }) => {
      this.updateForm(nieruchomosc);
    });
    this.adresService.query({ filter: 'nieruchomosc-is-null' }).subscribe(
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
  }

  updateForm(nieruchomosc: INieruchomosc) {
    this.editForm.patchValue({
      id: nieruchomosc.id,
      typ: nieruchomosc.typ,
      iloscMieszkan: nieruchomosc.iloscMieszkan,
      iloscMieszkancow: nieruchomosc.iloscMieszkancow,
      adres: nieruchomosc.adres
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nieruchomosc = this.createFromForm();
    if (nieruchomosc.id !== undefined) {
      this.subscribeToSaveResponse(this.nieruchomoscService.update(nieruchomosc));
    } else {
      this.subscribeToSaveResponse(this.nieruchomoscService.create(nieruchomosc));
    }
  }

  private createFromForm(): INieruchomosc {
    return {
      ...new Nieruchomosc(),
      id: this.editForm.get(['id']).value,
      typ: this.editForm.get(['typ']).value,
      iloscMieszkan: this.editForm.get(['iloscMieszkan']).value,
      iloscMieszkancow: this.editForm.get(['iloscMieszkancow']).value,
      adres: this.editForm.get(['adres']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INieruchomosc>>) {
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
}
