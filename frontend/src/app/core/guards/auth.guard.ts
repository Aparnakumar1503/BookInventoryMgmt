import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';

export const authGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  if (!authService.isAuthenticated()) {
    notificationService.error('Login is required before opening protected endpoints.');
    return router.createUrlTree(['/']);
  }

  const moduleId = route.paramMap.get('id') ?? route.paramMap.get('moduleId');
  if (moduleId && !authService.canAccessModule(moduleId)) {
    notificationService.error('This login does not have access to the selected module.');
    return router.createUrlTree(['/']);
  }

  return true;
};
