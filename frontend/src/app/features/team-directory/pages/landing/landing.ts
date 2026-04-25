import { AfterViewInit, Component, ElementRef, OnDestroy, ViewChild, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleService } from '../../../../core/services/module.service';
import { ModuleConfig } from '../../../../core/models/module.model';

interface LandingModuleSummary {
  readonly id: string;
  readonly ownerName: string;
  readonly moduleName: string;
  readonly endpointCount: number;
}

interface Particle {
  x: number;
  y: number;
  vx: number;
  vy: number;
  radius: number;
  phase: number;
  color: readonly [number, number, number];
}

@Component({
  selector: 'app-landing',
  imports: [RouterLink],
  templateUrl: './landing.html',
  styleUrl: './landing.css'
})
export class LandingComponent implements AfterViewInit, OnDestroy {
  @ViewChild('backgroundCanvas', { static: true })
  private readonly backgroundCanvas?: ElementRef<HTMLCanvasElement>;

  private readonly moduleService = inject(ModuleService);
  private readonly palette: readonly (readonly [number, number, number])[] = [
    [78, 139, 143],
    [61, 122, 126],
    [90, 122, 106],
    [122, 128, 96],
    [138, 122, 82]
  ];

  private particles: Particle[] = [];
  private animationFrameId: number | null = null;
  private tick = 0;
  private width = 0;
  private height = 0;

  readonly modules = this.moduleService.getModules();
  readonly stats = this.moduleService.getStats();
  readonly moduleSummaries: readonly LandingModuleSummary[] = this.modules.map((module: ModuleConfig) => ({
    id: module.id,
    ownerName: module.owner.name,
    moduleName: module.name,
    endpointCount: module.endpoints.length
  }));

  ngAfterViewInit(): void {
    this.initializeCanvas();
  }

  ngOnDestroy(): void {
    if (this.animationFrameId !== null) {
      cancelAnimationFrame(this.animationFrameId);
    }
    window.removeEventListener('resize', this.handleResize);
  }

  private initializeCanvas(): void {
    const canvas = this.backgroundCanvas?.nativeElement;
    if (!canvas) {
      return;
    }

    this.particles = Array.from({ length: 80 }, () => this.createParticle());
    this.resizeCanvas();
    window.addEventListener('resize', this.handleResize);
    this.drawFrame();
  }

  private readonly handleResize = (): void => {
    this.resizeCanvas();
  };

  private resizeCanvas(): void {
    const canvas = this.backgroundCanvas?.nativeElement;
    if (!canvas) {
      return;
    }

    this.width = window.innerWidth;
    this.height = window.innerHeight;
    canvas.width = this.width;
    canvas.height = this.height;
  }

  private createParticle(): Particle {
    const color = this.palette[Math.floor(Math.random() * this.palette.length)];
    return {
      x: Math.random(),
      y: Math.random(),
      vx: (Math.random() - 0.5) * 0.00016,
      vy: (Math.random() - 0.5) * 0.00016,
      radius: Math.random() * 1.3 + 0.3,
      phase: Math.random() * Math.PI * 2,
      color
    };
  }

  private drawFrame(): void {
    const canvas = this.backgroundCanvas?.nativeElement;
    const context = canvas?.getContext('2d');
    if (!canvas || !context) {
      return;
    }

    context.clearRect(0, 0, this.width, this.height);
    this.drawGrid(context);
    this.drawParticles(context);
    this.drawConnections(context);
    this.tick += 1;
    this.animationFrameId = requestAnimationFrame(() => this.drawFrame());
  }

  private drawGrid(context: CanvasRenderingContext2D): void {
    const offset = (this.tick * 0.2) % 42;

    for (let x = offset; x < this.width; x += 42) {
      context.strokeStyle = 'rgba(78,139,143,0.04)';
      context.lineWidth = 0.5;
      context.beginPath();
      context.moveTo(x, 0);
      context.lineTo(x, this.height);
      context.stroke();
    }

    for (let y = offset; y < this.height; y += 42) {
      context.strokeStyle = 'rgba(78,139,143,0.04)';
      context.lineWidth = 0.5;
      context.beginPath();
      context.moveTo(0, y);
      context.lineTo(this.width, y);
      context.stroke();
    }
  }

  private drawParticles(context: CanvasRenderingContext2D): void {
    this.particles.forEach((particle) => {
      particle.x += particle.vx;
      particle.y += particle.vy;
      particle.phase += 0.015;

      if (particle.x < 0) {
        particle.x = 1;
      } else if (particle.x > 1) {
        particle.x = 0;
      }

      if (particle.y < 0) {
        particle.y = 1;
      } else if (particle.y > 1) {
        particle.y = 0;
      }

      const alpha = 0.2 + 0.1 * Math.sin(particle.phase);
      const [red, green, blue] = particle.color;
      context.beginPath();
      context.arc(particle.x * this.width, particle.y * this.height, particle.radius, 0, Math.PI * 2);
      context.fillStyle = `rgba(${red},${green},${blue},${alpha})`;
      context.fill();
    });
  }

  private drawConnections(context: CanvasRenderingContext2D): void {
    for (let index = 0; index < this.particles.length; index += 1) {
      for (let inner = index + 1; inner < this.particles.length; inner += 1) {
        const dx = (this.particles[index].x - this.particles[inner].x) * this.width;
        const dy = (this.particles[index].y - this.particles[inner].y) * this.height;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 76) {
          context.strokeStyle = `rgba(78,139,143,${(1 - distance / 76) * 0.065})`;
          context.lineWidth = 0.3;
          context.beginPath();
          context.moveTo(this.particles[index].x * this.width, this.particles[index].y * this.height);
          context.lineTo(this.particles[inner].x * this.width, this.particles[inner].y * this.height);
          context.stroke();
        }
      }
    }
  }
}
