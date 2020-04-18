import { Component, OnInit } from '@angular/core';
import {Course} from "../../_models/course";
import {Lesson} from "../../_models/lesson";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs/operators";
import {LessonService} from "../../_services/lesson.service";
import {MaterialService} from "../../_services/material.service";
import {Material} from "../../_models/material";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.css']
})
export class LessonComponent implements OnInit {

  name: string;
  lesson: Lesson = {} as Lesson;
  private sub: Subscription;
  fileToUpload: File = null;
  materials: Material[];
  downloadUrl: string;


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private lessonService: LessonService,
    private toastr: ToastrService,
    private materialService: MaterialService
  ) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.name = params['name'];
      this.loadLesson(this.name);
    });
    this.downloadUrl = LessonService.DOWNLOAD_URL_MATERIAL;
  }

  loadLesson(name: string) {
    this.lessonService.getLesson(name)
      .pipe(first())
      .subscribe( res => {
          this.lesson = res;
        },
        error => {
          console.log(error);
        });
    this.lessonService.getLessonMaterialsByName(name)
      .pipe(first())
      .subscribe( res => {
          this.materials = res;
        },
        error => {
          console.log(error);
        });
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToActivity() {
    if(!this.fileToUpload){
      this.toastr.error("Файл не выбран");
    }
    else {
      this.materialService.postFile(this.fileToUpload, this.name).subscribe(data => {
        this.toastr.success("Вы успешно загрузили материал");
        this.reloadComponent();
      }, error => {
        console.log(error);
      });
    }

  }

  onSubmit() {
    this.uploadFileToActivity();
  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/lesson', this.name]);
  }

  delete(id) {
    this.materialService.delete(id).subscribe(data => {
      this.toastr.success("Вы успешно удалили материал");
      this.reloadComponent();
    }, error => {
      console.log(error);
    });
  }
}
