insert into roles values (5, 'Superuser') ON CONFLICT DO NOTHING;
insert into roles values (4, 'Admin') ON CONFLICT DO NOTHING;
insert into roles values (3, 'Delivery') ON CONFLICT DO NOTHING;
insert into roles values (2, 'Service') ON CONFLICT DO NOTHING;
insert into roles values (1, 'Consumer') ON CONFLICT DO NOTHING;
insert into kyc values (1, 'Aadhaar', 'Signed E-Aadhar or Front and back Aadhaar Card scan/photo')  ON CONFLICT DO NOTHING;
insert into kyc values (2, 'PAN', 'PAN card E-PAN or Front PAN card scan/photo')  ON CONFLICT DO NOTHING;
insert into kyc values (3, 'Driving License', 'Driving License back side scan/photo')  ON CONFLICT DO NOTHING;
insert into kyc values (4, 'Passport', 'Passport first and last page scan only')  ON CONFLICT DO NOTHING;