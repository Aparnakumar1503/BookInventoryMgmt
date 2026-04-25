import { Routes } from '@angular/router';
import { EndpointFormComponent } from './features/endpoint-executor/pages/endpoint-form/endpoint-form';
import { EndpointResultComponent } from './features/result-viewer/pages/endpoint-result/endpoint-result';
import { LandingComponent } from './features/team-directory/pages/landing/landing';
import { ModuleHomeComponent } from './features/member-workspace/pages/module-home/module-home';
import { ModulesComponent } from './features/modules/modules.component';
import { LoginComponent } from './features/login/login.component';
import { NotFoundComponent } from './features/not-found/pages/not-found/not-found';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'modules', component: ModulesComponent },
  { path: 'login/:moduleId', component: LoginComponent },
  { path: 'member/:id', component: ModuleHomeComponent, canActivate: [authGuard] },
  { path: 'endpoint-form/:moduleId/:endpointId', component: EndpointFormComponent, canActivate: [authGuard] },
  { path: 'result', component: EndpointResultComponent, canActivate: [authGuard] },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];
