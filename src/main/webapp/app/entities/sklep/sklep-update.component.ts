import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISklep, Sklep } from 'app/shared/model/sklep.model';
import { SklepService } from './sklep.service';
import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';
import { GodzinyOtwarciaService } from 'app/entities/godziny-otwarcia/godziny-otwarcia.service';

@Component({
  selector: 'jhi-sklep-update',
  templateUrl: './sklep-update.component.html'
})
export class SklepUpdateComponent implements OnInit {
  isSaving: boolean;

  godzinyotwarcias: IGodzinyOtwarcia[];

  editForm = this.fb.group({
    id: [],
    typ: [],
    godzinyOtwarcia: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sklepService: SklepService,
    protected godzinyOtwarciaService: GodzinyOtwarciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sklep }) => {
      this.updateForm(sklep);
    });
    this.godzinyOtwarciaService.query({ filter: 'sklep-is-null' }).subscribe(
      (res: HttpResponse<IGodzinyOtwarcia[]>) => {
        if (!this.editForm.get('godzinyOtwarcia').value || !this.editForm.get('godzinyOtwarcia').value.id) {
          this.godzinyotwarcias = res.body;
        } else {
          this.godzinyOtwarciaService
            .find(this.editForm.get('godzinyOtwarcia').value.id)
            .subscribe(
              (subRes: HttpResponse<IGodzinyOtwarcia>) => (this.godzinyotwarcias = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(sklep: ISklep) {
    this.editForm.patchValue({
      id: sklep.id,
      typ: sklep.typ,
      godzinyOtwarcia: sklep.godzinyOtwarcia
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sklep = this.createFromForm();
    if (sklep.id !== undefined) {
      this.subscribeToSaveResponse(this.sklepService.update(sklep));
    } else {
      this.subscribeToSaveResponse(this.sklepService.create(sklep));
    }
  }

  private createFromForm(): ISklep {
    return {
      ...new Sklep(),
      id: this.editForm.get(['id']).value,
      typ: this.editForm.get(['typ']).value,
      godzinyOtwarcia: this.editForm.get(['godzinyOtwarcia']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISklep>>) {
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

  trackGodzinyOtwarciaById(index: number, item: IGodzinyOtwarcia) {
    return item.id;
  }
}
