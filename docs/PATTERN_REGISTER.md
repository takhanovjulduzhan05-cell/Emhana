# PATTERN REGISTER — Emhana (M1–M3, v0.3)

| # | Pattern / category | Problem addressed | Classes | JUnit test | Alternative |
|---|---|---|---|---|---|
| 1 | Factory Method (Creational) | Numbered queue tickets may have distinct rules | TicketFactory, RegularTicketFactory, PriorityTicketFactory | factoryMethodHasIndependentTicketVariants | switch on ticket type |
| 2 | Builder (Creational) | Booking requires several mandatory fields | Appointment.Builder | builderRequiresFields | large constructor |
| 3 | Adapter (Structural) | Old SMS gateway signature differs from ReminderPort | SmsReminderAdapter, LegacySmsGateway | observerAndAdapterDispatchNotifications | call gateway directly |
| 4 | Facade (Structural) | UI needs one entry point for queue rendering | QueueFacade | facadeHidesQueueConstructionAndCancellationExcludesEntry | duplicate list/sort logic in UI |
| 5 | Proxy (Structural) | Appointment access depends on staff role | RoleAccessProxy, RealAppointmentAccess | proxyRestrictsAccess | access checks in console |
| 6 | Observer (Behavioral) | Booking events should trigger reminders | BookingObserver, ReminderObserver | observerAndAdapterDispatchNotifications | hardwired SMS call |
| 7 | Command (Behavioral) | Cancellation must be reversible | CancelAppointmentCommand, CommandHistory | commandUndoRestoresAppointmentAndSlot | ad hoc restore flag in UI |
| 8 | State (Behavioral) | Slot availability controls valid actions | SlotState, AvailableState, ReservedState | stateRejectsDoubleBooking | boolean plus if chains |
| 9 | Strategy (Behavioral) | Queue ordering should be extendable | QueueStrategy, BySlotQueueStrategy, ByPatientQueueStrategy | strategyIsReplaceableWithoutChangingService | switch in facade |
| 10 | Iterator (Behavioral) | Queue must traverse a stable snapshot | QueueSnapshot | iteratorGivesStableSnapshot | expose mutable repository collection |
| 11 | Chain of Responsibility (Behavioral) | Booking request needs independent validation steps | BookingValidator, PatientValidator, SlotValidator | chainRejectsInvalidRequest | one large validation method |

## Composed patterns
- **Observer + Adapter:** `AppointmentService` emits events; `ReminderObserver` sends notifications through `ReminderPort` implemented by `SmsReminderAdapter`.
- **Facade + Strategy + Factory Method + Iterator:** `QueueFacade` orders queue entries, issues ticket codes, then iterates a stable snapshot.
- **Chain + State:** request validation checks availability and `SlotState.reserve()` enforces the invariant.

## Rejected patterns
| Pattern | Reason | ADR |
|---|---|---|
| Singleton | Hidden global state makes tests and parallel clinics harder | docs/adr/0005-pattern-audit.md |
| Mediator | No complex peer-to-peer communication; observers and a service are sufficient | docs/adr/0005-pattern-audit.md |

*Chain is introduced ahead of the M4 schedule because it belongs to the chosen clinic topic; file/SQLite persistence and 60% CI coverage remain M4 deliverables.*
