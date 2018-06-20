{Program Sample2
{Function second PRAJ
{if {< PRAJ 2.3333 }
then {= back -2}
else {= back 1}
{do > VAL 0 
{= back {* back PRAJ}}
{= VAL {- VAL 1}}
} while
}
return retVal or 33
}
{Function third PRAJ3
{if {< PRAJ3 0 }
then {= backlo -2}
else {= backlo 1}
{do {> VAL 0} 
{= retVal {* retVal VAL}}
{= VAL {- VAL 1}
while
}
return retVal or 33
}
{print {second - 1.25}}
}