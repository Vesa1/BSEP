export class NewCertificate {
    constructor(
        public commonName: string,
        public country: string,
        public surname: string,
        public givenName: string,
        public organization: string,
        public organizationalUnit: string,
        public selfSigned: boolean,
        public issuer: string,
        public serialNumber: string, 
        public email: string,
        public certificateType: string
    ) {}
}
