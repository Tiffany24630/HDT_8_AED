import simpy
import random
import matplotlib.pyplot as plt

random.seed(10)

class Paciente:
    def __init__(self, id, severidad):
        self.id = id
        self.severidad = severidad

def simulacion(num_enfermeras=2, num_doctores=3, num_rayos_x=1):
    env = simpy.Environment()
    enfermeras = simpy.PriorityResource(env, capacity=num_enfermeras)
    doctores = simpy.PriorityResource(env, capacity=num_doctores)
    rayos_x = simpy.PriorityResource(env, capacity=num_rayos_x)
    tiempos_espera = []

    def llegada_pacientes(env):
        paciente_id = 0
        while True:
            yield env.timeout(random.expovariate(1/10))  # Llegada cada 10 min
            paciente_id += 1
            severidad = random.randint(1, 5)
            paciente = Paciente(paciente_id, severidad)
            env.process(triage(env, paciente, enfermeras, doctores, rayos_x, tiempos_espera))

    def triage(env, paciente, enfermeras, doctores, rayos_x, tiempos_espera):
        inicio = env.now
        with enfermeras.request(priority=paciente.severidad) as req:
            yield req
            yield env.timeout(10)  # Tiempo de triage: 10 min
            # Procesar con doctor
            with doctores.request(priority=paciente.severidad) as req_doc:
                yield req_doc
                yield env.timeout(30)  # Tiempo consulta: 30 min
                # Rayos X si es necesario
                with rayos_x.request(priority=paciente.severidad) as req_rx:
                    yield req_rx
                    yield env.timeout(20)  # Tiempo rayos X: 20 min
        tiempos_espera.append(env.now - inicio)

    env.process(llegada_pacientes(env))
    env.run(until=480)  # Simular 8 horas (480 minutos)
    return sum(tiempos_espera) / len(tiempos_espera) if tiempos_espera else 0

# Variar recursos y graficar
recursos = [(2,3,1), (3,4,2), (4,5,2)]
tiempos = []
for config in recursos:
    tiempo_promedio = simulacion(*config)
    tiempos.append(tiempo_promedio)

plt.bar(range(len(recursos)), tiempos, tick_label=[str(c) for c in recursos])
plt.xlabel("Configuración (enfermeras, doctores, rayos X)")
plt.ylabel("Tiempo promedio de espera (min)")
plt.title("Impacto de recursos en tiempo de espera")
plt.show()